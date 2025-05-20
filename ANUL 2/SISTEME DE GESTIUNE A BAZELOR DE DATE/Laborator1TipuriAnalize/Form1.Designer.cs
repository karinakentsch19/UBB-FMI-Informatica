namespace Laborator1TipuriAnalize
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
            dataGridViewParent = new DataGridView();
            dataGridViewChild = new DataGridView();
            ButonIncarcareParinti = new Button();
            buttonInsert = new Button();
            buttonDelete = new Button();
            buttonUpdate = new Button();
            GenereazaTextBoxeButton = new Button();
            textboxesPanel = new Panel();
            ((System.ComponentModel.ISupportInitialize)dataGridViewParent).BeginInit();
            ((System.ComponentModel.ISupportInitialize)dataGridViewChild).BeginInit();
            SuspendLayout();
            // 
            // dataGridViewParent
            // 
            dataGridViewParent.ColumnHeadersHeightSizeMode = DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            dataGridViewParent.Location = new Point(48, 71);
            dataGridViewParent.Name = "dataGridViewParent";
            dataGridViewParent.RowHeadersWidth = 51;
            dataGridViewParent.Size = new Size(391, 493);
            dataGridViewParent.TabIndex = 0;
            dataGridViewParent.RowHeaderMouseClick += dataGridViewTipuriAnalize_RowHeaderMouseClick;
            // 
            // dataGridViewChild
            // 
            dataGridViewChild.ColumnHeadersHeightSizeMode = DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            dataGridViewChild.Location = new Point(507, 71);
            dataGridViewChild.Name = "dataGridViewChild";
            dataGridViewChild.RowHeadersWidth = 51;
            dataGridViewChild.Size = new Size(601, 192);
            dataGridViewChild.TabIndex = 1;
            dataGridViewChild.RowHeaderMouseClick += dataGridViewAnalize_RowHeaderMouseClick;
            // 
            // ButonIncarcareParinti
            // 
            ButonIncarcareParinti.Location = new Point(149, 12);
            ButonIncarcareParinti.Name = "ButonIncarcareParinti";
            ButonIncarcareParinti.Size = new Size(161, 29);
            ButonIncarcareParinti.TabIndex = 2;
            ButonIncarcareParinti.Text = "Incarcare Parinti";
            ButonIncarcareParinti.UseVisualStyleBackColor = true;
            ButonIncarcareParinti.MouseClick += ButonIncarcareParinti_MouseClick;
            // 
            // buttonInsert
            // 
            buttonInsert.Location = new Point(549, 535);
            buttonInsert.Name = "buttonInsert";
            buttonInsert.Size = new Size(94, 29);
            buttonInsert.TabIndex = 3;
            buttonInsert.Text = "Insert";
            buttonInsert.UseVisualStyleBackColor = true;
            buttonInsert.MouseClick += buttonInsert_MouseClick;
            // 
            // buttonDelete
            // 
            buttonDelete.Location = new Point(946, 535);
            buttonDelete.Name = "buttonDelete";
            buttonDelete.Size = new Size(94, 29);
            buttonDelete.TabIndex = 4;
            buttonDelete.Text = "Delete";
            buttonDelete.UseVisualStyleBackColor = true;
            buttonDelete.MouseClick += buttonDelete_MouseClick;
            // 
            // buttonUpdate
            // 
            buttonUpdate.Location = new Point(744, 535);
            buttonUpdate.Name = "buttonUpdate";
            buttonUpdate.Size = new Size(94, 29);
            buttonUpdate.TabIndex = 5;
            buttonUpdate.Text = "Update";
            buttonUpdate.UseVisualStyleBackColor = true;
            buttonUpdate.MouseClick += buttonUpdate_MouseClick;
            // 
            // GenereazaTextBoxeButton
            // 
            GenereazaTextBoxeButton.Location = new Point(715, 12);
            GenereazaTextBoxeButton.Name = "GenereazaTextBoxeButton";
            GenereazaTextBoxeButton.Size = new Size(190, 29);
            GenereazaTextBoxeButton.TabIndex = 6;
            GenereazaTextBoxeButton.Text = "Genereaza Textbox";
            GenereazaTextBoxeButton.UseVisualStyleBackColor = true;
            GenereazaTextBoxeButton.Click += GenereazaTextBoxeButton_Click;
            // 
            // textboxesPanel
            // 
            textboxesPanel.Location = new Point(507, 288);
            textboxesPanel.Name = "textboxesPanel";
            textboxesPanel.Size = new Size(601, 229);
            textboxesPanel.TabIndex = 7;
            // 
            // Form1
            // 
            AutoScaleDimensions = new SizeF(8F, 20F);
            AutoScaleMode = AutoScaleMode.Font;
            ClientSize = new Size(1148, 590);
            Controls.Add(textboxesPanel);
            Controls.Add(GenereazaTextBoxeButton);
            Controls.Add(buttonUpdate);
            Controls.Add(buttonDelete);
            Controls.Add(buttonInsert);
            Controls.Add(ButonIncarcareParinti);
            Controls.Add(dataGridViewChild);
            Controls.Add(dataGridViewParent);
            Name = "Form1";
            Text = "Form1";
            ((System.ComponentModel.ISupportInitialize)dataGridViewParent).EndInit();
            ((System.ComponentModel.ISupportInitialize)dataGridViewChild).EndInit();
            ResumeLayout(false);
        }

        #endregion

        private DataGridView dataGridViewParent;
        private DataGridView dataGridViewChild;
        private Button ButonIncarcareParinti;
        private Button buttonInsert;
        private Button buttonDelete;
        private Button buttonUpdate;
        private Button GenereazaTextBoxeButton;
        private Panel textboxesPanel;
    }
}
