public class Product
{
    public int id;
    public string name;
    public int quantity;
    public double price;
    public string company;

    public Product(int id, string name, int quantity, double price, string company)
    {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.company = company;
    }
}
