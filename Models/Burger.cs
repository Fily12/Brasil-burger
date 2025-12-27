namespace Brasil_BurgerC.Models
{
    public class Burger
    {
        public int Id { get; set; }
        public string Nom { get; set; } = string.Empty;
        public string Description { get; set; } = string.Empty;
        public decimal Prix { get; set; }

        // Pour l’image dans le catalogue
        public string ImageUrl { get; set; } = "/images/default-burger.jpg";
    }
}
