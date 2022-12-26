package com.rbt.comunity.model;

import com.rbt.comunity.R;

import java.util.ArrayList;

public class DataCom {
    public static String [][] data = new String[][]{
            {"Harley", String.valueOf(R.drawable.harley)},
            {"Harley", String.valueOf(R.drawable.harley)},
            {"Harley", String.valueOf(R.drawable.harley)},
            {"Harley", String.valueOf(R.drawable.harley)},
            {"Harley", String.valueOf(R.drawable.harley)},
            {"Harley", String.valueOf(R.drawable.harley)},
            {"Harley", String.valueOf(R.drawable.harley)},
            {"Harley", String.valueOf(R.drawable.harley)},
            {"Harley", String.valueOf(R.drawable.harley)},
            {"Harley", String.valueOf(R.drawable.harley)},
            {"Harley", String.valueOf(R.drawable.harley)},
    };

    public static ArrayList<ModelPost> getData() {
        ArrayList<ModelPost> dataCard = new ArrayList<>();
        for (String[] varData : data) {
            ModelPost model = new ModelPost();
            model.setCom(varData[0]);
            model.setImg(Integer.parseInt(varData[1]));
            dataCard.add(model);
        }
        return dataCard;
    }
}
