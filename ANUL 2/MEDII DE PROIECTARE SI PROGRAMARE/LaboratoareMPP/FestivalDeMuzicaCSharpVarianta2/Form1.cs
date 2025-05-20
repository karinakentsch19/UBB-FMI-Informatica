using FestivalDeMuzicaCSharp.Business;
using FestivalDeMuzicaCSharp.Domain;

namespace FestivalDeMuzicaCSharpVarianta2
{
    public partial class Form1 : Form
    {
        IService service;

        public Form1(IService service)
        {
            this.service = service;
            InitializeComponent();
        }

        private void loginButton_Click(object sender, EventArgs e)
        {
            try
            {
                string username = usernameTextBox.Text;
                string password = passwordTextBox.Text;

                Employee employee = service.findEmployeeByUsernameAndPassword(username, password);

                Form2 form2 = new Form2(service, employee);
                form2.TopLevel = false;
                form2.FormBorderStyle = FormBorderStyle.None;
                form2.Dock = DockStyle.Fill;

                this.Controls.Clear();
                this.Controls.Add(form2);
                form2.Show();
            }
            catch(Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }
    }
}
