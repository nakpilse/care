/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import nse.dcfx.mysql.SQLModel;

/**
 *
 * @author Nsoft
 */
public class PharmacyOrderItem extends SQLModel<PharmacyOrderItem>{
    
    public static final String TABLE_NAME = "pharmacyorderitems";
    public static final String JOIN_KEY = "pharmacyorderitem_id";
    
    public static final String DESCRIPTION = "description";
    public static final String QUANTITY = "quantity";
    public static final String PHARMACYORDER_ID = PharmacyOrder.JOIN_KEY;
    public static final String ITEM_ID = Item.JOIN_KEY;
    
}
