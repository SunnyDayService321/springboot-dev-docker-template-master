package com.example.demo.resolvers;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.example.demo.dto.Payload;
import com.example.demo.models.UserEntity;
import com.example.demo.services.AccountService;

import lombok.Data; // Getter/Setter用

@Controller
public class AccountResolver {
    
    @Autowired
    private AccountService accountService;

    /**
     * GQL-AUTH-01: login Mutation の実装
     */
    @MutationMapping
    public Payload login(@Argument String email, @Argument String password) {
    	// バリデーションチェック
        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            throw new RuntimeException("BAD_USER_INPUT");// 設計書のエラーコード
        }
     // サービスを呼び出して認証
        UserEntity user = accountService.login(email, password);
        if (user == null) {
     // 認証失敗時はエラーを投げるか null を返す
            throw new RuntimeException("UNAUTHENTICATED");
        }
        
     // RequestContextHolderを使用してセッションにユーザーIDを保存
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        if (attr != null) {
            HttpSession session = attr.getRequest().getSession(true);
            session.setAttribute("userId", user.getId());
        }
        
        String token = "eyJhbGciOiJIUzI1Ni..." + user.getId();
        Payload payload = new Payload();
        payload.setToken(token);
        payload.setUser(user);
        return payload;
    }

    /**
     * GQL-AUTH-04: register Mutation の実装
     */
//    @MutationMapping
//    public Payload register(@Argument RegisterInput input) {
//        // 1. Serviceを呼び出して登録処理を実行
//        // 内部でバリデーション、重複チェック、ハッシュ化、保存が行われる
//        UserEntity user = accountService.register(
//            input.getEmail(), 
//            input.getPassword(), 
//            input.getPasswordConfirm()
//        );
//
//        // 2. 登録成功時、そのままログイン状態にするためJWTトークンを生成
//        // 設計書の「ビジネスロジック 6」に基づきトークンとユーザー情報を返却
//        String token = "eyJhbGciOiJIUzI1Ni..." + user.getId();
//        
//        Payload payload = new Payload();
//        payload.setToken(token);
//        payload.setUser(user);
//        
//        return payload;
//    }

    /**
     * 新規登録処理
     */
    @MutationMapping
    public Payload register(@Argument("input") Map<String, String> input) {
        // GraphQLの input: { email, password, passwordConfirm } を分解
        String email = input.get("email");
        String password = input.get("password");
        String passwordConfirm = input.get("passwordConfirm");

        // Serviceを呼び出し
        UserEntity user = accountService.register(email, password, passwordConfirm);

        // 結果をPayloadに詰めて返す
        Payload payload = new Payload();
        payload.setUser(user);
        payload.setToken("dummy-token-for-now"); // 必要に応じてトークン生成
        return payload;
    }
    
    
    /**
     * 会員情報更新処理
     *  RequestContextHolderを使用して、メソッド内部でセッションとユーザーIDを取得します
     */
    @MutationMapping
    public UserEntity updateProfile(@Argument UpdateProfileInput input) {
        // セッションからログイン中のユーザーIDを取得
    	ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

    	if (attr == null) {
            throw new RuntimeException("INTERNAL_SERVER_ERROR");
        }
        
        HttpSession session = attr.getRequest().getSession(false);

        // ログイン状態の確認（セッションがない、またはuserIdがない場合はエラー）
        if (session == null || session.getAttribute("userId") == null) {
            throw new RuntimeException("UNAUTHENTICATED");
        }

        // セッションからログイン中のユーザーIDを取り出す
        Long userId = (Long) session.getAttribute("userId");
        
        
     // Service層へ処理を委譲してデータベースを更新
        return accountService.updateProfile(
            userId, 
            input.getEmail(), 
            input.getPassword(), 
            input.getPasswordConfirm()
        );
    }

    /**
     * プロフィール更新用入力データクラス
     */
    @Data
    public static class UpdateProfileInput {
        private String email;
        private String password;
        private String passwordConfirm;
        
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
        public String getPasswordConfirm() { return passwordConfirm; }
        public void setPasswordConfirm(String passwordConfirm) { this.passwordConfirm = passwordConfirm; }
    }

    /**
     * 新規登録用入力データクラス
     * GQL-AUTH-04 の input RegisterInput に対応するクラス
　   * フィールド名は schema.graphqls と一致させる必要がある
     */
    @Data
    public static class RegisterInput {
        private String email;
        private String password;
        private String passwordConfirm;
        
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
        public String getPasswordConfirm() { return passwordConfirm; }
        public void setPasswordConfirm(String passwordConfirm) { this.passwordConfirm = passwordConfirm; }
    }

}