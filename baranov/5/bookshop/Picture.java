package bookshop;

public class Picture extends ItemForSale {
    private Painter painters;
    private String style;

    public Picture(String itemName, int yearOfItem, int price, Painter painters, String style) throws Exceptions.InvalidPriceException {
        super(itemName, yearOfItem, price);
        this.painters = painters;
        this.style = style;
    }

    @Override
    public String getDescription() {
        return "Нарисована: " + painters.getFirstName() + " " + painters.getLastName() + ", в " + getYearOfItem() + ", в стиле " + style;
    }
}
