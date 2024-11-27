package com.task.crud.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Сущность Phone.
 * Используется для маппинга с таблицей phone в базе данных.
 *
 * @author Zabnev Konstantin
 * @version 1.0
 * @since 2024-11-26
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Phone {
    /**
     * Уникальный идентификатор телефона.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    /**
     * Название бренда телефона.
     * Ограничения: не может быть пустым или null, а также длина не может превышать 256 символов
     */
    @Column(name = "brand")
    private String brand;
    /**
     * Название модели телефона.
     * Ограничения: не может быть пустым или null, а также длина не может превышать 256 символов
     */
    @Column(name = "model")
    private String model;
    /**
     * Цена телефона.
     */
    @Column(name = "price")
    private double price;
}
