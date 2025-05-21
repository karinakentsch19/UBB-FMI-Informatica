package com.example.comenzirestaurant.Service;

import com.example.comenzirestaurant.Domain.MenuItem;
import com.example.comenzirestaurant.Repository.MenuItemRepository;

public class MenuItemService extends AbstractService{
    private MenuItemRepository menuItemRepository;

    public MenuItemService(MenuItemRepository menuItemRepository) {
        this.menuItemRepository = menuItemRepository;
    }

    public Iterable<MenuItem> getAllMenuItemsByCategory(String category){
        return menuItemRepository.getAllMenuItemsByCategory(category);
    }

    public Iterable<String> getAllCategories(){
        return menuItemRepository.getAllCategories();
    }
}
