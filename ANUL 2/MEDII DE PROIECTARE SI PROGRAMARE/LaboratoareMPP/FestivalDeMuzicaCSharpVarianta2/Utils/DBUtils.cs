using System.Data;
using System.Data.SQLite;
using FestivalDeMuzicaCSharp.Domain;
using log4net.Appender;

namespace FestivalDeMuzicaCSharp.Utils;

public class DBUtils
{
    private IDictionary<string, string> props;
    
    private static IDbConnection Instance = null;

    public DBUtils(IDictionary<string, string> properties)
    {
        this.props = properties;
    }

    public IDbConnection getConnection()
    {
        if (Instance == null || Instance.State == System.Data.ConnectionState.Closed)
        {
            Instance = getNewConnection();
            Instance.Open();
        }

        return Instance;
    }

    private IDbConnection getNewConnection()
    {
        string connectionString = props["connectionString"];
        return new SQLiteConnection(connectionString);
    }
}