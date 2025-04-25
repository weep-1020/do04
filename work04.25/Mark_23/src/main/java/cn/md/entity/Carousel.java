package cn.md.entity;


//轮播图
public class Carousel {
    private int carousel_id;
    private String description;
    private String pname;
    private Object content;

    private int ifOpen;

    public Carousel() {
    }

    public Carousel(int carousel_id, String description, String pname, Object content, int ifOpen) {
        this.carousel_id = carousel_id;
        this.description = description;
        this.pname = pname;
        this.content = content;
        this.ifOpen = ifOpen;
    }

    public int getCarousel_id() {
        return carousel_id;
    }

    public void setCarousel_id(int carousel_id) {
        this.carousel_id = carousel_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    public int getIfOpen() {
        return ifOpen;
    }

    public void setIfOpen(int ifOpen) {
        this.ifOpen = ifOpen;
    }

    @Override
    public String toString() {
        return "Carousel{" +
                "carousel_id=" + carousel_id +
                ", description='" + description + '\'' +
                ", pname='" + pname + '\'' +
                ", content=" + content +
                ", ifOpen=" + ifOpen +
                '}';
    }
}
