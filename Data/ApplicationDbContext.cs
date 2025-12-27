using Microsoft.AspNetCore.Identity.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore;
using Brasil_BurgerC.Models;

namespace Brasil_BurgerC.Data
{
    public class ApplicationDbContext : IdentityDbContext
    {
        public ApplicationDbContext(DbContextOptions<ApplicationDbContext> options)
            : base(options) { }

        // Tes tables côté client
        public DbSet<Client> Clients { get; set; }
        public DbSet<Burger> Burgers { get; set; }
        public DbSet<Menu> Menus { get; set; }
        public DbSet<Commande> Commandes { get; set; }
        public DbSet<Paiement> Paiements { get; set; }
        public DbSet<Complement> Complements { get; set; }
    }
}
