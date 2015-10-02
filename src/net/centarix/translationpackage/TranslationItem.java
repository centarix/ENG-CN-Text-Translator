/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.centarix.translationpackage;
import java.util.Date;
import javax.persistence.*;

/**
 *
 * @author klove
 */
@Entity
@Table(name = "dbo.TranslationItem")
public class TranslationItem {
    @Id @GeneratedValue (strategy = GenerationType.IDENTITY)
    long Id;
    String EnglishText;
    String ChineseText;
    String PinYin;
    Date CreatedDate;
    int LookupCount;
    
    public TranslationItem() {
        
    }
    
    public TranslationItem(String englishText, String chineseText, String pinyin) {
        this.EnglishText = englishText.toLowerCase();
        this.ChineseText = chineseText;
        this.PinYin = pinyin;
        this.CreatedDate = new Date();
        this.LookupCount = 1;
    }
    
    public String getEnglishText() {
        return this.EnglishText;
    }
    public String getChineseText() {
        return this.ChineseText;
    }
    public String getPinYin() {
        return this.PinYin;
    }
    public Date getCreatedDate() {
        return this.CreatedDate;
    }
    public int getLookupCount() {
        return this.LookupCount;
    }
    
    public void setEnglishText(String eText) {
        this.EnglishText = eText.toLowerCase();
    }
    
    public void setChineseText(String cText) {
        this.ChineseText = cText;
    }
    
    public void setCreatedDate(Date date) {
        this.CreatedDate = date;
    }
    
    public void setPinYin(String pText) {
        this.PinYin = pText;
    }
    
    public void incrementLookup(int lookupCount)
    {
        this.LookupCount++;
    }
}
