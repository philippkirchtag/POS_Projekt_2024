using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.IO;
using System.Net.Http;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Input;

namespace Notes_WPF_POS_PROJEKT2024
{
    public partial class MainWindow : Window
    {
        List<Note> notes = new List<Note>();

        public MainWindow()
        {
            InitializeComponent();
            LoadNotesAsync();
        }

        private async void LoadNotesAsync()
        {
            notes = await NoteService.GetNotesAsync();
            contentAddToLB();
        }

        private async void onAddBtnClick(object sender, RoutedEventArgs e)
        {
            NoteWindow noteWindow = new NoteWindow();
            noteWindow.Title = "New Note";
            noteWindow.ShowDialog();

            if (noteWindow.DialogResult == true)
            {
                Note newNote = new Note
                {
                    Title = noteWindow.TitleText,
                    Content = noteWindow.ContentText
                };
                await NoteService.AddNoteAsync(newNote);
                LoadNotesAsync();
            }
        }

        private async void btn_delete_Click(object sender, RoutedEventArgs e)
        {
            if (lb_Content.SelectedItem != null)
            {
                Note selectedNote = notes[lb_Content.SelectedIndex];
                await NoteService.DeleteNoteAsync(selectedNote.Id);
                LoadNotesAsync();
            }
        }

        private async void NoteSelect(object sender, MouseButtonEventArgs e)
        {
            if (lb_Content.SelectedItem != null)
            {
                Note selectedNote = notes[lb_Content.SelectedIndex];
                NoteWindow noteWindow = new NoteWindow();
                noteWindow.Title = "Notiz - " + selectedNote.Title;// + " - " + Convert.ToString(selectedNote.CreatedDate)
                noteWindow.ContentText = selectedNote.Content;
                noteWindow.TitleText = selectedNote.Title;
                noteWindow.ID = selectedNote.Id;
                noteWindow.ShowDialog();

                if (noteWindow.DialogResult == true)
                {
                    selectedNote.Title = noteWindow.TitleText;
                    selectedNote.Content = noteWindow.ContentText;
                    await NoteService.UpdateNoteAsync(selectedNote);
                    LoadNotesAsync();
                }
            }
        }

        public void contentAddToLB()
        {
            lb_Content.Items.Clear();
            foreach (Note note in notes)
            {
                lb_Content.Items.Add($"{note.Title}");// - (Erstellt am: {note.CreatedDate})
            }
        }
    }
}
