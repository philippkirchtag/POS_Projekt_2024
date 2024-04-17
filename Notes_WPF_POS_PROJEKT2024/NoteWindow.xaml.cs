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
        private int noteID;
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

        public int ID
        {
            get { return noteID; }
            set { noteID = value; }
        }

        public NoteWindow()
        {
            InitializeComponent();
        }

        private void onSaveBtnClick(object sender, RoutedEventArgs e)
        {
            bool add = true;
            try
            {
                string json = File.ReadAllText("notes.json");
                notes = JsonConvert.DeserializeObject<List<Note>>(json);
            }
            catch (Exception ex)
            {
                MessageBox.Show($"Fehler beim Deserialisieren der JSON-Datei: {ex.Message}");
            }

            if (noteID == 0)
            {
                noteID = notes.Count + 1;
            }

            // ein label machen bei erstellen mit id übergeben und dann hier auslesen
            foreach(Note n in notes)
            {
                if (n.NoteID == noteID+notes.Count)
                {
                    add = false;
                    n.Title = tb_title.Text;
                    n.Content = tb_content.Text;
                }
            }
            if (add)
            {
                Note n = new Note();
                n.Title=tb_title.Text;
                n.Content=tb_content.Text;
                notes.Add(n);
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
