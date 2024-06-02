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
    public partial class NoteWindow : Window
    {
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

        public string ID { get; set; }

        public NoteWindow()
        {
            InitializeComponent();
        }

        private void onSaveBtnClick(object sender, RoutedEventArgs e)
        {
            DialogResult = true;
            Close();
        }
    }
}
