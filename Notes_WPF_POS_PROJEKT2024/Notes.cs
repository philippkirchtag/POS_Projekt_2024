using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Notes_WPF_POS_PROJEKT2024
{
    internal class Notes
    {
        public List<Note> NoteList { get; set; }

        public Notes()
        {
            NoteList = new List<Note>();
        }
    }
}
