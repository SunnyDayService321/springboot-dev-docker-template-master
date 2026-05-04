package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.demo.models.ItemEntity;
import com.example.demo.repositries.ItemRepository;

@Service
public class FavoriteService {

    @Autowired
    private ItemRepository itemRepository;

    @Transactional
    public boolean toggle(Long id) {
        // 1. データの取得
        ItemEntity item = itemRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("商品が見つかりません"));
        
        // 2. 状態の反転
        boolean newStatus = !item.isFavorite();
        item.setFavorite(newStatus);
        
        // 3. DBへ保存
        itemRepository.saveAndFlush(item);
        
        return newStatus;
    }
}