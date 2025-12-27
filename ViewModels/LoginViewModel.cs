// ViewModels/LoginViewModel.cs
using System.ComponentModel.DataAnnotations;

namespace Brasil_BurgerC.ViewModels
{
   public class LoginViewModel
{
    public required string EmailOrPhone { get; set; }
    public required string Password { get; set; }
}

}
