using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Notes_WPF_POS_PROJEKT2024
{
    internal class Note
    {
        private static int nextID = 1;

        public int NoteID { get; private set; }
        public string Title { get; set; }
        public string Content { get; set; }
        public DateTime CreatedDate { get; private set; }

        public Note()
        {
            NoteID = nextID++;
            CreatedDate = DateTime.Now;
        }

        public Note(string title, string content) : this()
        {
            Title = title;
            Content = content;
        }

        public override string ToString()
        {
            return $"{Title} (Erstellt am: {CreatedDate})";
        }
    }
}
