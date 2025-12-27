namespace Brasil_BurgerC.Models
{
    public class Paiement
    {
        public int Id { get; set; }
        public string Mode { get; set; } = string.Empty; // Carte, Cash, etc.
        public decimal Montant { get; set; }

        public int CommandeId { get; set; }
        public Commande Commande { get; set; } = new Commande();
    }
}
