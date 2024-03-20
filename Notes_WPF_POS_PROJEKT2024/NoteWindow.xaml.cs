using System;
using System.Collections.Generic;
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
        public string TitleText
        {
            //get { return lbl_Title.Text; }
            set { lbl_title.Content = value; }
        }

        public string ContentText
        {
            //get { return lbl_content.Content; }
            set { lbl_content.Content = value; }
        }

        public NoteWindow()
        {
            InitializeComponent();
        }
    }

}
