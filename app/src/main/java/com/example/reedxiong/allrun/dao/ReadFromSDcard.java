package com.example.reedxiong.allrun.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Created by Reed.Xiong on 2016/7/26.
 */
public class ReadFromSDcard {
    public static  String base64FromFile(String filepath){
        File file=new File(filepath);
        try {
            FileInputStream fileInputStream=new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
