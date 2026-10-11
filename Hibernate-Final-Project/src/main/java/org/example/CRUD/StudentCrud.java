package org.example.CRUD;

import org.hibernate.cfg.Configuration;
public class StudentCrud {
    public static void main(){
        Configuration cfg = new Configuration().configure("hibernate.cfg.xml");
    }
}
