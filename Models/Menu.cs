namespace Brasil_BurgerC.Models
{
    public class Menu
    {
        public int Id { get; set; }
        public string Nom { get; set; } = string.Empty;
        public decimal Prix { get; set; }

        public ICollection<Burger> Burgers { get; set; } = new List<Burger>();
        public ICollection<Commande> Commandes { get; set; } = new List<Commande>();
    }
}
