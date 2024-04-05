using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Shapes;

namespace Notes_WPF_POS_PROJEKT2024
{
    /// <summary>
    /// Interaktionslogik für NoteWindow.xaml
    /// </summary>
    public partial class NoteWindow : Window
    {
        List<Note> notes = new List<Note>();
        public string TitleText
        {
            get { return tb_title.Text; }
            set { tb_title.Text = value; }
        }

        public string ContentText
        {
            get { return tb_content.Text; }
            set { tb_content.Text = value; }
        }

        public string ID
        {
            get { return lbl_id.Text; }
            set { lbl_id.Text = value; }
        }

        public NoteWindow()
        {
            InitializeComponent();
        }

        private void onSaveBtnClick(object sender, RoutedEventArgs e)
        {
            try
            {
                string json = File.ReadAllText("notes.json");
                notes = JsonConvert.DeserializeObject<List<Note>>(json);
            }
            catch (Exception ex)
            {
                MessageBox.Show($"Fehler beim Deserialisieren der JSON-Datei: {ex.Message}");
            }

            if(lbl_id.Text=="")
            {
                lbl_id.Text = Convert.ToString(notes.Count + 1);
            }

            // ein label machen bei erstellen mit id übergeben und dann hier auslesen
            foreach(Note n in notes)
            {
                if (n.NoteID == Convert.ToInt32(lbl_id.Text)+3)
                {
                    n.Title = tb_title.Text;
                    n.Content = tb_content.Text;
                }
            }

            try
            {
                string json = JsonConvert.SerializeObject(notes, Formatting.Indented);
                File.WriteAllText("notes.json", json);
                MessageBox.Show("Notizen erfolgreich gespeichert.", "Erfolg", MessageBoxButton.OK, MessageBoxImage.Information);
            }
            catch (Exception ex)
            {
                MessageBox.Show($"Fehler beim Speichern der Notizen: {ex.Message}", "Fehler", MessageBoxButton.OK, MessageBoxImage.Error);
            }
        }
    }

}
