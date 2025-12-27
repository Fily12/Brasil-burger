namespace Brasil_BurgerC.Models
{
    public class Complement
    {
        public int Id { get; set; }
        public string Nom { get; set; } = string.Empty;
        public decimal Prix { get; set; }

        public ICollection<Commande> Commandes { get; set; } = new List<Commande>();
    }
}
