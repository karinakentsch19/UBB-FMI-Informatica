using FestivalDeMuzicaCSharp.Business;
using FestivalDeMuzicaCSharp.Domain;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace FestivalDeMuzicaCSharpVarianta2
{
    public partial class Form2 : Form
    {

        IService service;

        Employee employee;

        public Form2(IService service, Employee employee)
        {
            this.service = service;
            this.employee = employee;
            InitializeComponent();
            concertsDataGridView.RowPrePaint += concertsDataGridView_RowPrePaint;
            initGridViews();
            initConcerts();
        }

        private void initGridViews()
        {
            concertsDataGridView.AutoGenerateColumns = false;
            concertsDataGridView.Columns["artistFirstnameConcerts"].DataPropertyName = "artistFirstname";
            concertsDataGridView.Columns["artistLastnameConcerts"].DataPropertyName = "artistLastname";
            concertsDataGridView.Columns["concertIdConcerts"].DataPropertyName = "concertId";
            concertsDataGridView.Columns["concertNameConcerts"].DataPropertyName = "concertName";
            concertsDataGridView.Columns["dateConcerts"].DataPropertyName = "date";
            concertsDataGridView.Columns["addressConcerts"].DataPropertyName = "address";
            concertsDataGridView.Columns["noOfAvailableSeatsConcerts"].DataPropertyName = "numberOfAvailableSeats";
            concertsDataGridView.Columns["noOfSoldSeatsConcerts"].DataPropertyName = "numberOfSoldSeats";

            concertsByDateDataGridView.AutoGenerateColumns = false;
            concertsByDateDataGridView.Columns["artistFirstnameConcertsByDate"].DataPropertyName = "artistFirstname";
            concertsByDateDataGridView.Columns["artistLastnameConcertsByDate"].DataPropertyName = "artistLastname";
            concertsByDateDataGridView.Columns["concertIdConcertsByDate"].DataPropertyName = "concertId";
            concertsByDateDataGridView.Columns["concertNameConcertsByDate"].DataPropertyName = "concertName";
            concertsByDateDataGridView.Columns["addressConcertsByDate"].DataPropertyName = "address";
            concertsByDateDataGridView.Columns["startTimeConcertsByDate"].DataPropertyName = "startTime";
            concertsByDateDataGridView.Columns["endTimeConcertsByDate"].DataPropertyName = "endTime";
            concertsByDateDataGridView.Columns["noOfAvailableSeatsConcertsByDate"].DataPropertyName = "numberOfAvailableSeats";
        }

        private void initConcerts()
        {
            List<ConcertDTO> concerts = service.getAllConcertData().ToList();
            BindingList<ConcertDTO> concertDTOs = new BindingList<ConcertDTO>(concerts);
            concertsDataGridView.DataSource = concertDTOs;
        }

        private void initConcertsByDate()
        {
            DateTime dateTime = dateTimePicker.Value;
            DateOnly date = DateOnly.FromDateTime(dateTime);
            List<ConcertDayDTO> concerts = service.getAllConcertsByDate(date).ToList();
            BindingList<ConcertDayDTO> concertDayDTOs = new BindingList<ConcertDayDTO>(concerts);
            concertsByDateDataGridView.DataSource = concertDayDTOs;
        }

        private void logoutButton_Click(object sender, EventArgs e)
        {
            try
            {
                Form1 form1 = new Form1(service);
                form1.TopLevel = false;
                form1.FormBorderStyle = FormBorderStyle.None;
                form1.Dock = DockStyle.Fill;

                this.Controls.Clear();
                this.Controls.Add(form1);
                form1.Show();
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private void buyTicketButton_Click(object sender, EventArgs e)
        {
            try
            {
                long concertId = long.Parse(concertIdTextBox.Text);
                string clientName = clientNameTextBox.Text;
                long numberOfTickets = long.Parse(noOfTicketsTextBox.Text);

                service.addTicket(employee.Id, concertId, clientName, DateTime.Now, numberOfTickets);
                concertIdTextBox.Clear();
                clientNameTextBox.Clear();
                noOfTicketsTextBox.Clear();

                initConcerts();
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private void searchButton_Click(object sender, EventArgs e)
        {
            try
            {
                initConcertsByDate();
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }
        private void concertsDataGridView_RowPrePaint(object sender, DataGridViewRowPrePaintEventArgs e)
        {
            int noOfAvailableSeatsColumnIndex = concertsDataGridView.Columns["noOfAvailableSeatsConcerts"].Index;
            int noOfAvailableSeats = Convert.ToInt32(concertsDataGridView.Rows[e.RowIndex].Cells[noOfAvailableSeatsColumnIndex].Value);

            // Check if the condition is met and set the row's foreground color accordingly
            if (noOfAvailableSeats == 0)
            {
                concertsDataGridView.Rows[e.RowIndex].DefaultCellStyle.ForeColor = Color.Red;
            }
            else
            {
                concertsDataGridView.Rows[e.RowIndex].DefaultCellStyle.ForeColor = Color.Black;
            }
        }
    }
}
