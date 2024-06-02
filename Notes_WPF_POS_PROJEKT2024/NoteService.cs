using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Net.Http;
using System.Text;
using System.Threading.Tasks;

namespace Notes_WPF_POS_PROJEKT2024
{
    internal class NoteService
    {
        private static readonly HttpClient client = new HttpClient();
        private static readonly string baseUrl = "http://localhost:8080";

        public static async Task<List<Note>> GetNotesAsync()
        {
            HttpResponseMessage response = await client.GetAsync($"{baseUrl}/notes");
            response.EnsureSuccessStatusCode();
            string responseBody = await response.Content.ReadAsStringAsync();
            return JsonConvert.DeserializeObject<List<Note>>(responseBody);
        }

        public static async Task<Note> GetNoteAsync(string id)
        {
            HttpResponseMessage response = await client.GetAsync($"{baseUrl}/note/{id}");
            response.EnsureSuccessStatusCode();
            string responseBody = await response.Content.ReadAsStringAsync();
            return JsonConvert.DeserializeObject<Note>(responseBody);
        }

        public static async Task<Note> AddNoteAsync(Note note)
        {
            string json = JsonConvert.SerializeObject(note);
            StringContent content = new StringContent(json, Encoding.UTF8, "application/json");
            HttpResponseMessage response = await client.PostAsync($"{baseUrl}/note", content);
            response.EnsureSuccessStatusCode();
            string responseBody = await response.Content.ReadAsStringAsync();
            return JsonConvert.DeserializeObject<Note>(responseBody);
        }

        public static async Task<Note> UpdateNoteAsync(Note note)
        {
            string json = JsonConvert.SerializeObject(note);
            StringContent content = new StringContent(json, Encoding.UTF8, "application/json");
            HttpResponseMessage response = await client.PutAsync($"{baseUrl}/note", content);
            response.EnsureSuccessStatusCode();
            string responseBody = await response.Content.ReadAsStringAsync();
            return JsonConvert.DeserializeObject<Note>(responseBody);
        }

        public static async Task DeleteNoteAsync(string id)
        {
            HttpResponseMessage response = await client.DeleteAsync($"{baseUrl}/note/{id}");
            response.EnsureSuccessStatusCode();
        }
    }
}
