package com.uniovi.sdi.notaneitor.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PO_LoginView extends PO_NavView{

    static public void fillLoginForm(WebDriver driver, String dnip, String password) {
        WebElement dni = driver.findElement(By.name("username"));
        dni.click();
        dni.clear();
        dni.sendKeys(dnip);
        WebElement pwd = driver.findElement(By.name("password"));
        pwd.click();
        pwd.clear();
        pwd.sendKeys(password);
        By boton = By.className("btn");
        driver.findElement(boton).click();
    }
}
