package com.example.demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.demo.models.ItemEntity; // Entityを使用
import com.example.demo.models.ItemForm;
import com.example.demo.repositries.ItemRepository;

@Service
public class ItemService {
	@Autowired
    private ItemRepository itemRepository;
//
//    @Transactional
//    public boolean toggleFavorite(Long id) {
//        // 1. 棚(Repository)から本の中身(Entity)を取り出す
//        ItemEntity item = itemRepository.findById(id)
//            .orElseThrow(() -> new RuntimeException("商品が見つかりません"));
//        
//        // 2. お気に入り状態を反転させる
//        item.setFavorite(!item.isFavorite());
//        
//        // 3. 書き換えた本を棚に戻す
//        itemRepository.saveAndFlush(item);
//        
//        // 今の状態を報告する
//        return item.isFavorite();
//    }
//	
//    private boolean favorite;
//
//    public boolean isFavorite() {
//        return favorite;
//    }
//
//    public void setFavorite(boolean favorite) {
//        this.favorite = favorite;
//    }

	public List<ItemEntity> findAll() {
		return itemRepository.findAll();
	}

	public Optional<ItemEntity> findById(Long id) {
		return itemRepository.findById(id);
	}

	public void save(ItemForm itemForm) {
		// 1. 画面から送られてきた Form を、DB保存用の Entity に詰め替える
        ItemEntity entity = new ItemEntity();
        
        // IDがセットされていれば更新、なければ新規登録になります
        entity.setId(itemForm.getId()); 
        entity.setName(itemForm.getName());
        entity.setPrice(itemForm.getPrice());
        entity.setContent(itemForm.getContent());
        entity.setFavorite(itemForm.isFavorite());
        
        // 2. Repositoryを使って保存
        itemRepository.saveAndFlush(entity);
		
	}

	public void deleteById(Long id) {
		itemRepository.deleteById(id);
		
	}
}
