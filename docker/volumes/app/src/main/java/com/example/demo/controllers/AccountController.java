package com.example.demo.controllers;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.dto.Payload;
import com.example.demo.models.UserEntity;
import com.example.demo.services.AccountService;

@Controller
public class AccountController {
	
	@Autowired
    private AccountService accountService;
	
	/**
     * U-AUTH-01: ログイン画面を表示
     * 対応ファイル: templates/account/login.html
     */
    @GetMapping("/account/login")
    public String loginPage() {
        return "account/login";
    }
    
 // ログイン処理
    @PostMapping("/api/session/sync")
    @ResponseBody
    public ResponseEntity<?> syncSession(@RequestBody Map<String, Object> payload, HttpServletRequest request) {
        System.out.println("★DEBUG: 同期リクエストが届きました！ データ: " + payload);
        try {
            Object rawId = payload.get("userId");
            if (rawId == null) return ResponseEntity.badRequest().build();
            
            Long userId = Long.valueOf(rawId.toString());
            
            // セッションを作成・保存
            HttpSession session = request.getSession(true);
            session.setAttribute("userId", userId);
            
            System.out.println("★連携完了: セッションにユーザーID " + userId + " を登録しました");
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }
    
//    @PostMapping("/account/login")
//    public String login(@RequestParam String email, @RequestParam String password, HttpSession session, Model model) {
//        try {
//            // GraphQLのロジック（Service）を呼び出し、認証
//        	UserEntity user = accountService.login(email, password);
//            
//            // 成功したらセッションに保存 
//            session.setAttribute("userId", user.getId());
//            
//            // マイページへリダイレクト 
//            return "redirect:/account/profile";
//        } catch (Exception e) {
//            // 失敗したらログイン画面をエラー付きで再表示 [cite: 10, 16]
//            model.addAttribute("error", "メールアドレスまたはパスワードが違います");
//            return "account/login";
//        }
//    }
    /**
     * U-AUTH-03: 新規会員登録画面を表示 
     * 対応ファイル: templates/account/register.html
     */
    @GetMapping("/account/register")
    public String registerPage(HttpSession session) {
        // 1. セッションを確認し、ログイン済みの場合は /account/profile へリダイレクト 
        if (session.getAttribute("userId") != null) {
            return "redirect:/account/profile"; // 既存のプロフィールのURLに合わせる
        }
        // 2. 未ログインの場合、新規会員登録フォームの HTML を返す [cite: 6]
        return "account/register";
    }
    
    /**
     * U-AUTH-04: 新規会員登録処理
     */
    @PostMapping("/account/register")
    public String register(@RequestParam("email") String email, 
    		@RequestParam("password") String password,
    		@RequestParam("passwordConfirm") String passwordConfirm, 
            HttpSession session, Model model) {
        try {
            // 1. バリデーションと登録 (Serviceのloginではなく、registerを呼ぶ)
            UserEntity user = accountService.register(email, password, passwordConfirm);

            // 2. 登録成功：セッションにIDを保存 
            session.setAttribute("userId", user.getId());

            // 3. プロフィール画面へリダイレクト 
            return "redirect:/account/profile";

        } catch (RuntimeException e) {
        	// Serviceから投げられたエラーメッセージ（EMAIL_ALREADY_EXISTS等）を取得
            // 4. エラー発生（重複など）：メッセージを出してフォーム再表示 
        	e.printStackTrace();//黒い画面に何が出ているか見る
            String message = "登録に失敗しました" + e.getMessage();
            if ("EMAIL_ALREADY_EXISTS".equals(e.getMessage())) {
                message = "このメールアドレスはすでに登録されています"; 
            } else if ("PASSWORD_MISMATCH".equals(e.getMessage())) {
                message = "パスワードが一致しません"; 
            }
            model.addAttribute("error", message);
            return "account/register";
        }
    }

    /**
     * U-AUTH-05: 会員情報（マイページ）を表示
     */
    @GetMapping("/account/profile")
    public String profilePage(HttpSession session, Model model) {
    	System.out.println("セッションのID: " + session.getAttribute("userId"));
        // 1. セッションからログイン中のユーザーID（またはトークン）を取得
        Object userId = session.getAttribute("userId");

        // 2. 未ログインならログイン画面へリダイレクト（設計書の「302 Found」の記述通り）
        if (userId == null) {
            return "redirect:/account/login";
        }
        try {
        // 3. GQL-AUTH-05b (Query: me) に相当する処理でユーザー情報を取得
        // ※ ログイン中のIDを使ってDBから最新情報を引く
        UserEntity user = accountService.findUserById((Long) userId);

        // 4. 画面（Thymeleaf）に表示項目を渡す
	        model.addAttribute("user", user);
	        return "account/profile";
        } catch (Exception e) {
            // ユーザーが見つからない場合などのエラー処理
            return "redirect:/account/login";
        }
        
    }
    /**
     * U-AUTH-07: ログアウト処理
     * フォーム（POST）からのリクエストを受け取り、セッションを破棄してログイン画面へ戻る
     */
    @PostMapping("/account/logout")
    public String logout(HttpSession session) {
        // セッションを無効化（保持しているuserIdなどのデータをすべて消去）
        session.invalidate();
        
        // ログアウト後はログイン画面へリダイレクト
        return "redirect:/account/login";
    }

}
