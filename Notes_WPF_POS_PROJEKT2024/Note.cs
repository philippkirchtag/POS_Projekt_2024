using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Notes_WPF_POS_PROJEKT2024
{
    internal class Note
    {
        public string Id { get; set; }  // MongoDB Id
        public string Title { get; set; }
        public string Content { get; set; }
        /*public DateTime CreatedDate { get; set; }

        public Note()
        {
            CreatedDate = DateTime.Now;
        }*/

        public Note(string title, string content) : this()
        {
            Title = title;
            Content = content;
        }

        public Note()
        {
        }

        public override string ToString()
        {
            return $"{Title}"; //(Erstellt am: {CreatedDate})
        }
    }
}
