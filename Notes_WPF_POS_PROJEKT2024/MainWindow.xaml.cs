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
using System.Windows.Navigation;
using System.Windows.Shapes;

namespace Notes_WPF_POS_PROJEKT2024
{
    /// <summary>
    /// Interaction logic for MainWindow.xaml
    /// </summary>
    public partial class MainWindow : Window
    {
        List<Note> notes = new List<Note>();

        public MainWindow()
        {
            InitializeComponent();
            deserializeJSON();
            contentAddToLB();
        }

        public void deserializeJSON()
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
        }

        public void contentAddToLB()
        {
            int count=lb_Content.Items.Count;

            foreach (Note note in notes)
            {
                if(note.NoteID>count)
                {
                    lb_Content.Items.Add($"{note.Title} - (Erstellt am: {note.CreatedDate})");
                }
            }
        }

        public List<string> GetListBoxItems(ListBox listBox)
        {
            List<string> items = new List<string>();

            foreach (var item in listBox.Items)
            {
                items.Add(item.ToString());
            }

            return items;
        }

        private void NoteSelect(object sender, MouseButtonEventArgs e)
        {
            if (lb_Content.SelectedItem != null)
            {
                Note selectedNote = notes[lb_Content.SelectedIndex];
                ShowNoteWindow(selectedNote);
            }
        }

        private void ShowNoteWindow(Note note)
        {
            NoteWindow noteWindow = new NoteWindow();
            noteWindow.Title = "Notiz - " + note.Title + " - " + Convert.ToString(note.CreatedDate);
            noteWindow.ContentText = note.Content;
            noteWindow.TitleText = note.Title;
            noteWindow.ID = note.NoteID;
            //noteWindow.CreatedDate = note.CreatedDate;
            noteWindow.ShowDialog();
            deserializeJSON();
            contentAddToLB();
        }

        private void onAddBtnClick(object sender, RoutedEventArgs e)
        {
            NoteWindow noteWindow = new NoteWindow();
            noteWindow.Title = "New Note";
            //noteWindow.CreatedDate = note.CreatedDate;
            noteWindow.ShowDialog();
            deserializeJSON();
            contentAddToLB();
        }

        private void btn_delete_Click(object sender, RoutedEventArgs e)
        {

        }
    }
}
