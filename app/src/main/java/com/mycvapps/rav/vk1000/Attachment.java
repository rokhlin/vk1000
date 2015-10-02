package com.mycvapps.rav.vk1000;
/**
 * Базовый Класс для парсинга Attachment вложений, от него наследуются
 * все классы которые находятся в Attachment (Photo,video,...)
 */

public class Attachment {
    private String type;
    private int id;


    public Attachment(String type, int id) {
        this.type = type;
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


}
