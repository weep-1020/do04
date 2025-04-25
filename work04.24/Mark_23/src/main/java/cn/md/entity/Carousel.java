package cn.md.entity;


//轮播图
public class Carousel {
    private int carousel_id;
    private String description;
    private String image_id;
    private Object content;

    public Carousel() {
    }

    public Carousel(int carousel_id, String description, String image_id, String content) {
        this.carousel_id = carousel_id;
        this.description = description;
        this.image_id = image_id;
        this.content = content;
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

    public String getImage_id() {
        return image_id;
    }

    public void setImage_id(String image_id) {
        this.image_id = image_id;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Carousel{" +
                "carousel_id=" + carousel_id +
                ", description='" + description + '\'' +
                ", image_id='" + image_id + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
