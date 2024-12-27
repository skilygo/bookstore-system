package com.lty.bookstore.system.backend.api.controller;

import com.lty.bookstore.system.backend.api.message.UpdateStockRequest;
import com.lty.bookstore.system.backend.api.support.InventoryAssembler;
import com.lty.bookstore.system.backend.application.service.InventoryAppService;
import com.lty.bookstore.system.backend.base.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    private InventoryAppService inventoryAppService;

    @Autowired
    private InventoryAssembler inventoryAssembler;

    /**
     * search book's stock
     */
    @GetMapping("/{bookId}")
    public Response<?> getStock(@PathVariable String bookId) {
        int stock = inventoryAppService.getStock(bookId);
        return Response.success(stock);
    }

    /**
     * update book' stock
     */
    @PutMapping("/{bookId}")
    public Response<?> updateStock(@PathVariable String bookId, @RequestBody UpdateStockRequest updateStockRequest) {
        inventoryAppService.updateStock(bookId, updateStockRequest.getStock());
        return Response.success();
    }
}
