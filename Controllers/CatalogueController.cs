using Microsoft.AspNetCore.Mvc;
using Brasil_BurgerC.Data;
using Brasil_BurgerC.Models;

namespace Brasil_BurgerC.Controllers
{
    public class CatalogueController : Controller
    {
        private readonly ApplicationDbContext _context;

        public CatalogueController(ApplicationDbContext context)
        {
            _context = context;
        }

        public IActionResult Index()
        {
            var burgers = _context.Burgers.ToList();
            return View(burgers);
        }
    }
}
