package com.jjkj.guoyouchao.fangyou_food.UserDataModel;

import org.json.*;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;


/**
 * Created by guoyouchao on 16/7/10.
 */
public class UserData {

    private static UserData userdata;

    private Dictionary<String,FoodDataModel> FDM ;
    private Dictionary<String,TableSingModel> TSM;

    public static UserData getUserdata(){
        if(userdata == null){
           userdata = new UserData(new JSONObject());
        }
        return userdata;
    }


    public UserData(JSONObject data){

    }

    // 添加桌子
    public void addTableSingle(TableSingModel tsm){
        if(tsm != null){
            TSM.put(String.valueOf(tsm.getTableId()),tsm);
        }
    }

    // 删除桌子
    public void removeTableSingle(TableSingModel tsm){
        if(tsm != null){
            TSM.remove(tsm);
        }
    }

    // 添加菜单
    public void addFoodData(FoodDataModel fdm){
        if(fdm != null){
            FDM.put(String.valueOf(fdm.getFoodId()),fdm);
        }
    }

    // 删除菜单
    public void removeFoodData(FoodDataModel fdm){
        if(fdm != null){
            FDM.remove(fdm);
        }
    }

    // 获得餐桌数据
    public TableSingModel getTableSingModelById(String tableId){
        if(TSM.get(tableId) != null){
            return  TSM.get(tableId);
        }
        return null;
    }

    // 获得菜单数据
    public FoodDataModel getFoodDataModelById(String tableId){
        if(FDM.get(tableId) != null){
            return  FDM.get(tableId);
        }
        return null;
    }

    // 更新餐桌数据
    public void updateTableSingleModel(JSONObject obj){

    }


    // 餐厅餐桌数据



}
