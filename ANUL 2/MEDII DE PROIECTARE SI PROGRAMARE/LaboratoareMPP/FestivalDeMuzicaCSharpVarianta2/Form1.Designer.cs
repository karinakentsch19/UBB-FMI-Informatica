namespace FestivalDeMuzicaCSharpVarianta2
{
    partial class Form1
    {
        /// <summary>
        ///  Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        ///  Clean up any resources being used.
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
        ///  Required method for Designer support - do not modify
        ///  the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            panel1 = new Panel();
            loginButton = new Button();
            label3 = new Label();
            label2 = new Label();
            passwordTextBox = new TextBox();
            usernameTextBox = new TextBox();
            label1 = new Label();
            panel1.SuspendLayout();
            SuspendLayout();
            // 
            // panel1
            // 
            panel1.BackColor = Color.FromArgb(244, 243, 234);
            panel1.Controls.Add(loginButton);
            panel1.Controls.Add(label3);
            panel1.Controls.Add(label2);
            panel1.Controls.Add(passwordTextBox);
            panel1.Controls.Add(usernameTextBox);
            panel1.Controls.Add(label1);
            panel1.Location = new Point(303, 103);
            panel1.Name = "panel1";
            panel1.Size = new Size(660, 526);
            panel1.TabIndex = 0;
            // 
            // loginButton
            // 
            loginButton.BackColor = Color.FromArgb(215, 226, 245);
            loginButton.Font = new Font("Segoe UI", 28.2F, FontStyle.Regular, GraphicsUnit.Point, 0);
            loginButton.Location = new Point(220, 380);
            loginButton.Name = "loginButton";
            loginButton.Size = new Size(217, 94);
            loginButton.TabIndex = 5;
            loginButton.Text = "LOGIN";
            loginButton.UseVisualStyleBackColor = false;
            loginButton.Click += loginButton_Click;
            // 
            // label3
            // 
            label3.AutoSize = true;
            label3.Font = new Font("Segoe UI", 13.8F, FontStyle.Regular, GraphicsUnit.Point, 0);
            label3.Location = new Point(151, 277);
            label3.Name = "label3";
            label3.Size = new Size(132, 31);
            label3.TabIndex = 4;
            label3.Text = "PASSWORD";
            // 
            // label2
            // 
            label2.AutoSize = true;
            label2.Font = new Font("Segoe UI", 13.8F, FontStyle.Regular, GraphicsUnit.Point, 0);
            label2.Location = new Point(151, 179);
            label2.Name = "label2";
            label2.Size = new Size(133, 31);
            label2.TabIndex = 3;
            label2.Text = "USERNAME";
            // 
            // passwordTextBox
            // 
            passwordTextBox.Location = new Point(300, 283);
            passwordTextBox.Name = "passwordTextBox";
            passwordTextBox.PasswordChar = '*';
            passwordTextBox.Size = new Size(225, 27);
            passwordTextBox.TabIndex = 2;
            // 
            // usernameTextBox
            // 
            usernameTextBox.Location = new Point(300, 183);
            usernameTextBox.Name = "usernameTextBox";
            usernameTextBox.Size = new Size(225, 27);
            usernameTextBox.TabIndex = 1;
            // 
            // label1
            // 
            label1.AutoSize = true;
            label1.Font = new Font("Segoe UI", 60F, FontStyle.Bold, GraphicsUnit.Point, 0);
            label1.ForeColor = Color.FromArgb(62, 81, 114);
            label1.Location = new Point(151, 14);
            label1.Name = "label1";
            label1.Size = new Size(364, 133);
            label1.TabIndex = 0;
            label1.Text = "LOGIN";
            // 
            // Form1
            // 
            AutoScaleDimensions = new SizeF(8F, 20F);
            AutoScaleMode = AutoScaleMode.Font;
            BackColor = Color.FromArgb(62, 81, 114);
            ClientSize = new Size(1266, 732);
            Controls.Add(panel1);
            Name = "Form1";
            Text = "Form1";
            panel1.ResumeLayout(false);
            panel1.PerformLayout();
            ResumeLayout(false);
        }

        #endregion

        private Panel panel1;
        private Label label2;
        private TextBox passwordTextBox;
        private TextBox usernameTextBox;
        private Label label1;
        private Button loginButton;
        private Label label3;
    }
}
