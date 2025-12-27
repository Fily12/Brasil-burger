namespace Brasil_BurgerC.Models
{
    public class Commande
    {
        public int Id { get; set; }
        public DateTime DateCommande { get; set; }
        public decimal Total { get; set; }

        public int ClientId { get; set; }
        public Client Client { get; set; } = new Client();

        public ICollection<Burger> Burgers { get; set; } = new List<Burger>();
        public ICollection<Menu> Menus { get; set; } = new List<Menu>();
        public Paiement Paiement { get; set; } = new Paiement();
    }
}
