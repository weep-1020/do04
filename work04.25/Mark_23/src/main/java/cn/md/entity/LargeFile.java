package cn.md.entity;

public class LargeFile {
    private int pic;
    private String filename;
    private Object content;

    public LargeFile() {
    }

    public LargeFile(int pic, String filename, Object content) {
        this.pic = pic;
        this.filename = filename;
        this.content = content;
    }

    public int getPic() {
        return pic;
    }

    public void setPic(int pic) {
        this.pic = pic;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "LargeFile{" +
                "pic='" + pic + '\'' +
                ", filename='" + filename + '\'' +
                ", content=" + content +
                '}';
    }
}
