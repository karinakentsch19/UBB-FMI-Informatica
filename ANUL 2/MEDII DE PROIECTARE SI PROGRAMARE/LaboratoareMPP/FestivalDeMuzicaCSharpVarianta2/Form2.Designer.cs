namespace FestivalDeMuzicaCSharpVarianta2
{
    partial class Form2
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            panel1 = new Panel();
            searchButton = new Button();
            dateTimePicker = new DateTimePicker();
            concertsByDateDataGridView = new DataGridView();
            artistFirstnameConcertsByDate = new DataGridViewTextBoxColumn();
            artistLastnameConcertsByDate = new DataGridViewTextBoxColumn();
            concertIdConcertsByDate = new DataGridViewTextBoxColumn();
            concertNameConcertsByDate = new DataGridViewTextBoxColumn();
            addressConcertsByDate = new DataGridViewTextBoxColumn();
            startTimeConcertsByDate = new DataGridViewTextBoxColumn();
            endTimeConcertsByDate = new DataGridViewTextBoxColumn();
            noOfAvailableSeatsConcertsByDate = new DataGridViewTextBoxColumn();
            concertsDataGridView = new DataGridView();
            artistFirstnameConcerts = new DataGridViewTextBoxColumn();
            artistLastnameConcerts = new DataGridViewTextBoxColumn();
            concertIdConcerts = new DataGridViewTextBoxColumn();
            concertNameConcerts = new DataGridViewTextBoxColumn();
            dateConcerts = new DataGridViewTextBoxColumn();
            addressConcerts = new DataGridViewTextBoxColumn();
            noOfAvailableSeatsConcerts = new DataGridViewTextBoxColumn();
            noOfSoldSeatsConcerts = new DataGridViewTextBoxColumn();
            label6 = new Label();
            label1 = new Label();
            logoutButton = new Button();
            label2 = new Label();
            concertIdTextBox = new TextBox();
            clientNameTextBox = new TextBox();
            noOfTicketsTextBox = new TextBox();
            label3 = new Label();
            label4 = new Label();
            label5 = new Label();
            buyTicketButton = new Button();
            panel1.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)concertsByDateDataGridView).BeginInit();
            ((System.ComponentModel.ISupportInitialize)concertsDataGridView).BeginInit();
            SuspendLayout();
            // 
            // panel1
            // 
            panel1.BackColor = Color.FromArgb(211, 223, 245);
            panel1.Controls.Add(searchButton);
            panel1.Controls.Add(dateTimePicker);
            panel1.Controls.Add(concertsByDateDataGridView);
            panel1.Controls.Add(concertsDataGridView);
            panel1.Controls.Add(label6);
            panel1.Controls.Add(label1);
            panel1.Location = new Point(-9, 69);
            panel1.Name = "panel1";
            panel1.Size = new Size(1274, 448);
            panel1.TabIndex = 0;
            // 
            // searchButton
            // 
            searchButton.BackColor = Color.FromArgb(62, 81, 114);
            searchButton.Font = new Font("Segoe UI", 13.8F, FontStyle.Regular, GraphicsUnit.Point, 0);
            searchButton.ForeColor = Color.White;
            searchButton.Location = new Point(554, 391);
            searchButton.Name = "searchButton";
            searchButton.Size = new Size(130, 47);
            searchButton.TabIndex = 5;
            searchButton.Text = "SEARCH";
            searchButton.UseVisualStyleBackColor = false;
            searchButton.Click += searchButton_Click;
            // 
            // dateTimePicker
            // 
            dateTimePicker.Location = new Point(496, 358);
            dateTimePicker.Name = "dateTimePicker";
            dateTimePicker.Size = new Size(250, 27);
            dateTimePicker.TabIndex = 4;
            // 
            // concertsByDateDataGridView
            // 
            concertsByDateDataGridView.ColumnHeadersHeightSizeMode = DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            concertsByDateDataGridView.Columns.AddRange(new DataGridViewColumn[] { artistFirstnameConcertsByDate, artistLastnameConcertsByDate, concertIdConcertsByDate, concertNameConcertsByDate, addressConcertsByDate, startTimeConcertsByDate, endTimeConcertsByDate, noOfAvailableSeatsConcertsByDate });
            concertsByDateDataGridView.Location = new Point(21, 260);
            concertsByDateDataGridView.Name = "concertsByDateDataGridView";
            concertsByDateDataGridView.RowHeadersWidth = 51;
            concertsByDateDataGridView.Size = new Size(1204, 92);
            concertsByDateDataGridView.TabIndex = 3;
            // 
            // artistFirstnameConcertsByDate
            // 
            artistFirstnameConcertsByDate.HeaderText = "Artist Firstname";
            artistFirstnameConcertsByDate.MinimumWidth = 6;
            artistFirstnameConcertsByDate.Name = "artistFirstnameConcertsByDate";
            artistFirstnameConcertsByDate.Width = 150;
            // 
            // artistLastnameConcertsByDate
            // 
            artistLastnameConcertsByDate.HeaderText = "Artist Lastname";
            artistLastnameConcertsByDate.MinimumWidth = 6;
            artistLastnameConcertsByDate.Name = "artistLastnameConcertsByDate";
            artistLastnameConcertsByDate.Width = 150;
            // 
            // concertIdConcertsByDate
            // 
            concertIdConcertsByDate.HeaderText = "Concert id";
            concertIdConcertsByDate.MinimumWidth = 6;
            concertIdConcertsByDate.Name = "concertIdConcertsByDate";
            concertIdConcertsByDate.Width = 125;
            // 
            // concertNameConcertsByDate
            // 
            concertNameConcertsByDate.HeaderText = "Concert Name";
            concertNameConcertsByDate.MinimumWidth = 6;
            concertNameConcertsByDate.Name = "concertNameConcertsByDate";
            concertNameConcertsByDate.Width = 150;
            // 
            // addressConcertsByDate
            // 
            addressConcertsByDate.HeaderText = "Address";
            addressConcertsByDate.MinimumWidth = 6;
            addressConcertsByDate.Name = "addressConcertsByDate";
            addressConcertsByDate.Width = 125;
            // 
            // startTimeConcertsByDate
            // 
            startTimeConcertsByDate.HeaderText = "Start time";
            startTimeConcertsByDate.MinimumWidth = 6;
            startTimeConcertsByDate.Name = "startTimeConcertsByDate";
            startTimeConcertsByDate.Width = 125;
            // 
            // endTimeConcertsByDate
            // 
            endTimeConcertsByDate.HeaderText = "End time";
            endTimeConcertsByDate.MinimumWidth = 6;
            endTimeConcertsByDate.Name = "endTimeConcertsByDate";
            endTimeConcertsByDate.Width = 125;
            // 
            // noOfAvailableSeatsConcertsByDate
            // 
            noOfAvailableSeatsConcertsByDate.HeaderText = "No. of available seats";
            noOfAvailableSeatsConcertsByDate.MinimumWidth = 6;
            noOfAvailableSeatsConcertsByDate.Name = "noOfAvailableSeatsConcertsByDate";
            noOfAvailableSeatsConcertsByDate.Width = 200;
            // 
            // concertsDataGridView
            // 
            concertsDataGridView.ColumnHeadersHeightSizeMode = DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            concertsDataGridView.Columns.AddRange(new DataGridViewColumn[] { artistFirstnameConcerts, artistLastnameConcerts, concertIdConcerts, concertNameConcerts, dateConcerts, addressConcerts, noOfAvailableSeatsConcerts, noOfSoldSeatsConcerts });
            concertsDataGridView.Location = new Point(21, 44);
            concertsDataGridView.Name = "concertsDataGridView";
            concertsDataGridView.RowHeadersWidth = 51;
            concertsDataGridView.Size = new Size(1204, 170);
            concertsDataGridView.TabIndex = 1;
            concertsDataGridView.RowPrePaint += concertsDataGridView_RowPrePaint;
            // 
            // artistFirstnameConcerts
            // 
            artistFirstnameConcerts.HeaderText = "Artist Firstname";
            artistFirstnameConcerts.MinimumWidth = 6;
            artistFirstnameConcerts.Name = "artistFirstnameConcerts";
            artistFirstnameConcerts.Width = 150;
            // 
            // artistLastnameConcerts
            // 
            artistLastnameConcerts.HeaderText = "Artist Lastname";
            artistLastnameConcerts.MinimumWidth = 6;
            artistLastnameConcerts.Name = "artistLastnameConcerts";
            artistLastnameConcerts.Width = 150;
            // 
            // concertIdConcerts
            // 
            concertIdConcerts.HeaderText = "Concert id";
            concertIdConcerts.MinimumWidth = 6;
            concertIdConcerts.Name = "concertIdConcerts";
            concertIdConcerts.Width = 125;
            // 
            // concertNameConcerts
            // 
            concertNameConcerts.HeaderText = "Concert Name";
            concertNameConcerts.MinimumWidth = 6;
            concertNameConcerts.Name = "concertNameConcerts";
            concertNameConcerts.Width = 150;
            // 
            // dateConcerts
            // 
            dateConcerts.HeaderText = "Date";
            dateConcerts.MinimumWidth = 6;
            dateConcerts.Name = "dateConcerts";
            dateConcerts.Width = 125;
            // 
            // addressConcerts
            // 
            addressConcerts.HeaderText = "Address";
            addressConcerts.MinimumWidth = 6;
            addressConcerts.Name = "addressConcerts";
            addressConcerts.Width = 200;
            // 
            // noOfAvailableSeatsConcerts
            // 
            noOfAvailableSeatsConcerts.HeaderText = "No. of available seats";
            noOfAvailableSeatsConcerts.MinimumWidth = 6;
            noOfAvailableSeatsConcerts.Name = "noOfAvailableSeatsConcerts";
            noOfAvailableSeatsConcerts.Width = 125;
            // 
            // noOfSoldSeatsConcerts
            // 
            noOfSoldSeatsConcerts.HeaderText = "No. of sold seats";
            noOfSoldSeatsConcerts.MinimumWidth = 6;
            noOfSoldSeatsConcerts.Name = "noOfSoldSeatsConcerts";
            noOfSoldSeatsConcerts.Width = 125;
            // 
            // label6
            // 
            label6.AutoSize = true;
            label6.Font = new Font("Segoe UI", 13.8F, FontStyle.Bold, GraphicsUnit.Point, 0);
            label6.ForeColor = Color.FromArgb(62, 81, 114);
            label6.Location = new Point(554, 10);
            label6.Name = "label6";
            label6.Size = new Size(130, 31);
            label6.TabIndex = 0;
            label6.Text = "CONCERTS";
            // 
            // label1
            // 
            label1.AutoSize = true;
            label1.Font = new Font("Segoe UI", 13.8F, FontStyle.Bold, GraphicsUnit.Point, 0);
            label1.ForeColor = Color.FromArgb(62, 81, 114);
            label1.Location = new Point(502, 217);
            label1.Name = "label1";
            label1.Size = new Size(226, 31);
            label1.TabIndex = 2;
            label1.Text = "CONCERTS BY DATE";
            // 
            // logoutButton
            // 
            logoutButton.BackColor = Color.FromArgb(62, 81, 114);
            logoutButton.Font = new Font("Segoe UI", 16.2F, FontStyle.Regular, GraphicsUnit.Point, 0);
            logoutButton.ForeColor = Color.White;
            logoutButton.Location = new Point(1094, 5);
            logoutButton.Name = "logoutButton";
            logoutButton.Size = new Size(148, 58);
            logoutButton.TabIndex = 1;
            logoutButton.Text = "LOGOUT";
            logoutButton.UseVisualStyleBackColor = false;
            logoutButton.Click += logoutButton_Click;
            // 
            // label2
            // 
            label2.AutoSize = true;
            label2.Font = new Font("Segoe UI", 24F, FontStyle.Bold, GraphicsUnit.Point, 0);
            label2.ForeColor = Color.FromArgb(62, 81, 114);
            label2.Location = new Point(493, 517);
            label2.Name = "label2";
            label2.Size = new Size(244, 54);
            label2.TabIndex = 3;
            label2.Text = "BUY TICKET";
            // 
            // concertIdTextBox
            // 
            concertIdTextBox.Location = new Point(593, 574);
            concertIdTextBox.Name = "concertIdTextBox";
            concertIdTextBox.Size = new Size(182, 27);
            concertIdTextBox.TabIndex = 4;
            // 
            // clientNameTextBox
            // 
            clientNameTextBox.Location = new Point(593, 615);
            clientNameTextBox.Name = "clientNameTextBox";
            clientNameTextBox.Size = new Size(182, 27);
            clientNameTextBox.TabIndex = 5;
            // 
            // noOfTicketsTextBox
            // 
            noOfTicketsTextBox.Location = new Point(593, 656);
            noOfTicketsTextBox.Name = "noOfTicketsTextBox";
            noOfTicketsTextBox.Size = new Size(182, 27);
            noOfTicketsTextBox.TabIndex = 6;
            // 
            // label3
            // 
            label3.AutoSize = true;
            label3.Location = new Point(467, 577);
            label3.Name = "label3";
            label3.Size = new Size(92, 20);
            label3.TabIndex = 7;
            label3.Text = "CONCERT ID";
            // 
            // label4
            // 
            label4.AutoSize = true;
            label4.Location = new Point(467, 618);
            label4.Name = "label4";
            label4.Size = new Size(102, 20);
            label4.TabIndex = 8;
            label4.Text = "CLIENT NAME";
            // 
            // label5
            // 
            label5.AutoSize = true;
            label5.Location = new Point(467, 659);
            label5.Name = "label5";
            label5.Size = new Size(114, 20);
            label5.TabIndex = 9;
            label5.Text = "NO. OF TICKETS";
            // 
            // buyTicketButton
            // 
            buyTicketButton.BackColor = Color.FromArgb(62, 81, 114);
            buyTicketButton.Font = new Font("Segoe UI", 12F, FontStyle.Regular, GraphicsUnit.Point, 0);
            buyTicketButton.ForeColor = Color.White;
            buyTicketButton.Location = new Point(545, 689);
            buyTicketButton.Name = "buyTicketButton";
            buyTicketButton.Size = new Size(104, 38);
            buyTicketButton.TabIndex = 10;
            buyTicketButton.Text = "BUY";
            buyTicketButton.UseVisualStyleBackColor = false;
            buyTicketButton.Click += buyTicketButton_Click;
            // 
            // Form2
            // 
            AutoScaleDimensions = new SizeF(8F, 20F);
            AutoScaleMode = AutoScaleMode.Font;
            BackColor = Color.FromArgb(244, 243, 234);
            ClientSize = new Size(1266, 732);
            Controls.Add(buyTicketButton);
            Controls.Add(label5);
            Controls.Add(label4);
            Controls.Add(label3);
            Controls.Add(noOfTicketsTextBox);
            Controls.Add(clientNameTextBox);
            Controls.Add(concertIdTextBox);
            Controls.Add(label2);
            Controls.Add(logoutButton);
            Controls.Add(panel1);
            Name = "Form2";
            Text = "Form2";
            panel1.ResumeLayout(false);
            panel1.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)concertsByDateDataGridView).EndInit();
            ((System.ComponentModel.ISupportInitialize)concertsDataGridView).EndInit();
            ResumeLayout(false);
            PerformLayout();
        }

        #endregion

        private Panel panel1;
        private Button logoutButton;
        private Label label1;
        private Label label2;
        private TextBox concertIdTextBox;
        private TextBox clientNameTextBox;
        private TextBox noOfTicketsTextBox;
        private Label label3;
        private Label label4;
        private Label label5;
        private Button buyTicketButton;
        private DataGridView concertsDataGridView;
        private Label label6;
        private Button searchButton;
        private DateTimePicker dateTimePicker;
        private DataGridView concertsByDateDataGridView;
        private DataGridViewTextBoxColumn artistFirstnameConcertsByDate;
        private DataGridViewTextBoxColumn artistLastnameConcertsByDate;
        private DataGridViewTextBoxColumn concertIdConcertsByDate;
        private DataGridViewTextBoxColumn concertNameConcertsByDate;
        private DataGridViewTextBoxColumn addressConcertsByDate;
        private DataGridViewTextBoxColumn startTimeConcertsByDate;
        private DataGridViewTextBoxColumn endTimeConcertsByDate;
        private DataGridViewTextBoxColumn noOfAvailableSeatsConcertsByDate;
        private DataGridViewTextBoxColumn artistFirstnameConcerts;
        private DataGridViewTextBoxColumn artistLastnameConcerts;
        private DataGridViewTextBoxColumn concertIdConcerts;
        private DataGridViewTextBoxColumn concertNameConcerts;
        private DataGridViewTextBoxColumn dateConcerts;
        private DataGridViewTextBoxColumn addressConcerts;
        private DataGridViewTextBoxColumn noOfAvailableSeatsConcerts;
        private DataGridViewTextBoxColumn noOfSoldSeatsConcerts;
    }
}