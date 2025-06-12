package cn.md.entity;


//轮播图
public class Carousel {
    private int cid;
    private String description;
    private String pname;
    private Integer pic;

    private int ifOpen;

    public Carousel() {
    }

    public Carousel(int cid, String description, String pname, Integer pic, int ifOpen) {
        this.cid = cid;
        this.description = description;
        this.pname = pname;
        this.pic = pic;
        this.ifOpen = ifOpen;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
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

    public Integer getPic() {
        return pic;
    }

    public void setPic(Integer pic) {
        this.pic = pic;
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
                "cid=" + cid +
                ", description='" + description + '\'' +
                ", pname='" + pname + '\'' +
                ", pic=" + pic +
                ", ifOpen=" + ifOpen +
                '}';
    }
}
