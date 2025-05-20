using System.Data.SqlTypes;
using System.Data.SqlClient;
using System.Data;
using System.Configuration;
using System.Numerics;

namespace Laborator1TipuriAnalize
{
    public partial class Form1 : Form
    {
        readonly SqlDataAdapter adapter = new SqlDataAdapter();
        readonly DataSet dataSetParent = new DataSet();
        readonly DataSet dataSetChild = new DataSet();
        int idParent;
        int idChild;

        public Form1()
        {
            InitializeComponent();
        }

        private void dataGridViewTipuriAnalize_RowHeaderMouseClick(object sender, DataGridViewCellMouseEventArgs e)
        {
            idParent = int.Parse(dataGridViewParent.SelectedRows[0].Cells[0].Value.ToString());
            string connectionString = ConfigurationManager.ConnectionStrings["connectionDBCabinetMedical"].ConnectionString;
            try
            {
                SqlConnection sqlConnection = new SqlConnection(connectionString);
                string selectCommand = ConfigurationSettings.AppSettings["selectChild"];
                adapter.SelectCommand = new SqlCommand(selectCommand, sqlConnection);
                adapter.SelectCommand.Parameters.AddWithValue("@idParent", idParent);
                dataSetChild.Clear();
                adapter.Fill(dataSetChild);
                dataGridViewChild.DataSource = dataSetChild.Tables[0];  

            }catch (Exception ex) {
                MessageBox.Show(ex.Message);
            }

        }

        private int getColumnIndexForHeader(string header)
        {
            int columnIndex = 0;
            foreach (DataGridViewColumn col in dataGridViewChild.Columns)
            {
                if (col.HeaderText.Equals(header, StringComparison.OrdinalIgnoreCase))
                {
                    columnIndex = col.Index;
                    break;
                }
            }
            return columnIndex;
        }

        private void dataGridViewAnalize_RowHeaderMouseClick(object sender, DataGridViewCellMouseEventArgs e)
        {
            try
            {
                idChild = int.Parse(dataGridViewChild.SelectedRows[0].Cells[0].Value.ToString());


                List<string> ColumnNameList = new List<string>(ConfigurationSettings.AppSettings["ChildColumnNames"].Split(","));

                foreach (string columnName in ColumnNameList)
                {
                    TextBox textBox = (TextBox)textboxesPanel.Controls[columnName];
                    int columnIndex = getColumnIndexForHeader(columnName);
                    string value = dataGridViewChild.SelectedRows[0].Cells[columnIndex].Value.ToString();
                    textBox.Text = value;
                }


            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private void ButonIncarcareParinti_MouseClick(object sender, MouseEventArgs e)
        {
            string connectionString = ConfigurationManager.ConnectionStrings["connectionDBCabinetMedical"].ConnectionString;
            try
            {
                SqlConnection sqlConnection = new SqlConnection(connectionString);
                string selectCommand = ConfigurationSettings.AppSettings["selectParent"];
                adapter.SelectCommand = new SqlCommand(selectCommand, sqlConnection);       
                dataSetParent.Clear();
                adapter.Fill(dataSetParent);
                dataGridViewParent.DataSource = dataSetParent.Tables[0];    

            }catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }

        }

        private void buttonInsert_MouseClick(object sender, MouseEventArgs e)
        {
            string connectionString = ConfigurationManager.ConnectionStrings["connectionDBCabinetMedical"].ConnectionString;
            try
            {
                SqlConnection sqlConnection = new SqlConnection(connectionString);
                string childTableName = ConfigurationManager.AppSettings["ChildTableName"];
                string childColumnNames = ConfigurationManager.AppSettings["ChildColumnNames"];
                List<string> columnNames = new List<string>(ConfigurationManager.AppSettings["ChildColumnNames"].Split(','));

                string insertCommand = ConfigurationManager.AppSettings["InsertQuery"];
                SqlCommand sqlCommand = new SqlCommand(insertCommand, sqlConnection);

                sqlCommand.Parameters.AddWithValue("@idParent", idParent);

                foreach(string column in columnNames)
                {
                    TextBox text = (TextBox)textboxesPanel.Controls[column];
                    sqlCommand.Parameters.AddWithValue("@" + column, text.Text);   
                }

                sqlConnection.Open();
                sqlCommand.ExecuteNonQuery();
                dataSetChild.Clear();
                adapter.Fill(dataSetChild);
                dataGridViewChild.DataSource = dataSetChild.Tables[0];
                MessageBox.Show("Insert succesfully!");
                sqlConnection.Close();
                clearFields();
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private void buttonUpdate_MouseClick(object sender, MouseEventArgs e)
        {

            string connectionString = ConfigurationManager.ConnectionStrings["connectionDBCabinetMedical"].ConnectionString;
            try
            {
                SqlConnection sqlConnection = new SqlConnection(connectionString);
                string childTableName = ConfigurationManager.AppSettings["ChildTableName"];
                string childColumnNames = ConfigurationManager.AppSettings["ChildColumnNames"];
                List<string> columnNames = new List<string>(ConfigurationManager.AppSettings["ChildColumnNames"].Split(','));

                string updateCommand = ConfigurationManager.AppSettings["UpdateQuery"];
                SqlCommand sqlCommand = new SqlCommand(updateCommand, sqlConnection);

                sqlCommand.Parameters.AddWithValue("@id", idChild);


                foreach (string column in columnNames)
                {
                    TextBox text = (TextBox)textboxesPanel.Controls[column];
                    sqlCommand.Parameters.AddWithValue("@" + column, text.Text);
                }

                sqlConnection.Open();
                sqlCommand.ExecuteNonQuery();
                dataSetChild.Clear();
                adapter.Fill(dataSetChild);
                dataGridViewChild.DataSource = dataSetChild.Tables[0];
                MessageBox.Show("Update succesfully!");
                sqlConnection.Close();
                clearFields();
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private void buttonDelete_MouseClick(object sender, MouseEventArgs e)
        {
            string connectionString = ConfigurationManager.ConnectionStrings["connectionDBCabinetMedical"].ConnectionString;
            try
            {
                SqlConnection sqlConnection = new SqlConnection(connectionString);
               
                string deleteCommand = ConfigurationManager.AppSettings["DeleteQuery"];
                SqlCommand sqlCommand = new SqlCommand(deleteCommand, sqlConnection);

                sqlCommand.Parameters.AddWithValue("@id", idChild);        

                sqlConnection.Open();
                sqlCommand.ExecuteNonQuery();
                dataSetChild.Clear();
                adapter.Fill(dataSetChild);
                dataGridViewChild.DataSource = dataSetChild.Tables[0];
                MessageBox.Show("Delete succesfully!");
                sqlConnection.Close();
                clearFields();
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }

        }

        private void clearFields()
        {
            List<string> columnNames = new List<string>(ConfigurationManager.AppSettings["ChildColumnNames"].Split(','));
            foreach (string column in columnNames)
            {
                TextBox text = (TextBox)textboxesPanel.Controls[column];
                text.Text = text.Name;
            }
        }

        private void GenereazaTextBoxeButton_Click(object sender, EventArgs e)
        {
            try
            {
                List<string> columnNames = new List<string>(ConfigurationManager.AppSettings["ChildColumnNames"].Split(','));
                int pointX = 30;
                int pointY = 30;

                int numberOfColumns = int.Parse(ConfigurationManager.AppSettings["ChildNumberOfColumns"]);
                textboxesPanel.Controls.Clear();    

                foreach(string columnName in columnNames)
                {
                    TextBox textBox = new TextBox();
                    textBox.Text = columnName;
                    textBox.Name = columnName;
                    textBox.Location = new Point(pointX, pointY);
                    textBox.Visible = true;
                    textBox.Width = 400;
                    textBox.Parent = textboxesPanel;
                    textboxesPanel.Show();
                    pointY += 30;   
                }

            }catch(Exception ex) {
                MessageBox.Show(ex.Message);
            }
        }
    }
}
