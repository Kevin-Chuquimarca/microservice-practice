using Oracle.ManagedDataAccess.Client;

namespace service_company_c;

public class ProductRepository
{
    string conStringUser = "User Id=system" + ";Password=root" + ";Data Source=localhost/XE" + ";";
    public OracleConnection con;
    public OracleCommand cmd;

    public ProductRepository()
    {
        this.con = new OracleConnection(conStringUser);
        this.cmd = con.CreateCommand();
        try
        {
            this.con.Open();
        }
        catch (Exception e)
        {
            Console.WriteLine(e.Message);
        }
    }

    public void getListProducts()
    {
        try
        {
            cmd.CommandText = "SELECT name FROM PRODUCT";
            OracleDataReader reader = cmd.ExecuteReader();
            while (reader.Read())
            {
                Console.WriteLine("name:" + reader.GetString(0));
            }
            reader.Dispose();
        }
        catch (Exception e)
        {
            Console.WriteLine(e.Message);
        }
    }
}
