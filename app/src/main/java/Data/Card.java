package Data;

public class Card {
    private int image;
    private String title;
    private String introduce;
    private String price;

    public Card(int image, String title, String introduce, String price) {
        this.image = image;
        this.title = title;
        this.introduce = introduce;
        this.price = price;
    }

    public int getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public String getIntroduce() {
        return introduce;
    }

    public String getPrice() {
        return price;
    }
}
