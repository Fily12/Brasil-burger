namespace Brasil_BurgerC.Models
{
    public class Commande
{
    public int Id { get; set; }
    public DateTime DateCommande { get; set; }
    public decimal Total { get; set; }

    // Relation avec Burger
    public int BurgerId { get; set; }
    public Burger Burger { get; set; }

    // Mode de consommation
    public string ModeConsommation { get; set; } = string.Empty;

    // Paiement (simplifié en string pour éviter l’erreur)
    public string Paiement { get; set; } = string.Empty;
}

}
