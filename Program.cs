using Microsoft.AspNetCore.Identity;
using Microsoft.EntityFrameworkCore;
using Brasil_BurgerC.Data;

var builder = WebApplication.CreateBuilder(args);

// 1. Connexion à Neon via EF Core
builder.Services.AddDbContext<ApplicationDbContext>(options =>
    options.UseNpgsql(builder.Configuration.GetConnectionString("DefaultConnection")));

// 2. ASP.NET Identity pour gérer les comptes clients
builder.Services.AddDefaultIdentity<IdentityUser>(options =>
{
    options.SignIn.RequireConfirmedAccount = false;
    options.Password.RequireDigit = false;
    options.Password.RequireLowercase = false;
    options.Password.RequireUppercase = false;
    options.Password.RequireNonAlphanumeric = false;
    options.Password.RequiredLength = 6;
})
.AddEntityFrameworkStores<ApplicationDbContext>();

// 3. MVC
builder.Services.AddControllersWithViews();

var app = builder.Build();

// 4. Pipeline HTTP
if (!app.Environment.IsDevelopment())
{
    app.UseExceptionHandler("/Home/Error");
    app.UseHsts();
}

app.UseHttpsRedirection();
app.UseStaticFiles(); // ⚠️ important pour CSS/JS

app.UseRouting();

// 5. Authentification + Autorisation
app.UseAuthentication();
app.UseAuthorization();

// 6. Routes MVC
app.MapControllerRoute(
    name: "default",
    pattern: "{controller=Home}/{action=Index}/{id?}");

// ⚠️ Création de l’utilisateur de test au démarrage
using (var scope = app.Services.CreateScope())
{
    var userManager = scope.ServiceProvider.GetRequiredService<UserManager<IdentityUser>>();
    var user = userManager.FindByEmailAsync("filythiaw0@gmail.com").GetAwaiter().GetResult();
    if (user == null)
    {
        var newUser = new IdentityUser
        {
            UserName = "filythiaw0@gmail.com",
            Email = "filythiaw0@gmail.com"
        };
        userManager.CreateAsync(newUser, "fily123").GetAwaiter().GetResult();
    }
}

app.Run();
