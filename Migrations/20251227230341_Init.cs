using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace Brasil_BurgerC.Migrations
{
    /// <inheritdoc />
    public partial class Init : Migration
    {
        /// <inheritdoc />
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_Commandes_Clients_ClientId",
                table: "Commandes");

            migrationBuilder.DropTable(
                name: "BurgerCommande");

            migrationBuilder.DropTable(
                name: "BurgerMenu");

            migrationBuilder.DropTable(
                name: "CommandeMenu");

            migrationBuilder.DropIndex(
                name: "IX_Paiements_CommandeId",
                table: "Paiements");

            migrationBuilder.AlterColumn<int>(
                name: "ClientId",
                table: "Commandes",
                type: "integer",
                nullable: true,
                oldClrType: typeof(int),
                oldType: "integer");

            migrationBuilder.AddColumn<int>(
                name: "BurgerId",
                table: "Commandes",
                type: "integer",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "MenuId",
                table: "Commandes",
                type: "integer",
                nullable: true);

            migrationBuilder.AddColumn<string>(
                name: "ModeConsommation",
                table: "Commandes",
                type: "text",
                nullable: false,
                defaultValue: "");

            migrationBuilder.AddColumn<string>(
                name: "Paiement",
                table: "Commandes",
                type: "text",
                nullable: false,
                defaultValue: "");

            migrationBuilder.AddColumn<string>(
                name: "ImageUrl",
                table: "Burgers",
                type: "text",
                nullable: false,
                defaultValue: "");

            migrationBuilder.AddColumn<int>(
                name: "MenuId",
                table: "Burgers",
                type: "integer",
                nullable: true);

            migrationBuilder.CreateIndex(
                name: "IX_Paiements_CommandeId",
                table: "Paiements",
                column: "CommandeId");

            migrationBuilder.CreateIndex(
                name: "IX_Commandes_BurgerId",
                table: "Commandes",
                column: "BurgerId");

            migrationBuilder.CreateIndex(
                name: "IX_Commandes_MenuId",
                table: "Commandes",
                column: "MenuId");

            migrationBuilder.CreateIndex(
                name: "IX_Burgers_MenuId",
                table: "Burgers",
                column: "MenuId");

            migrationBuilder.AddForeignKey(
                name: "FK_Burgers_Menus_MenuId",
                table: "Burgers",
                column: "MenuId",
                principalTable: "Menus",
                principalColumn: "Id");

            migrationBuilder.AddForeignKey(
                name: "FK_Commandes_Burgers_BurgerId",
                table: "Commandes",
                column: "BurgerId",
                principalTable: "Burgers",
                principalColumn: "Id",
                onDelete: ReferentialAction.Cascade);

            migrationBuilder.AddForeignKey(
                name: "FK_Commandes_Clients_ClientId",
                table: "Commandes",
                column: "ClientId",
                principalTable: "Clients",
                principalColumn: "Id");

            migrationBuilder.AddForeignKey(
                name: "FK_Commandes_Menus_MenuId",
                table: "Commandes",
                column: "MenuId",
                principalTable: "Menus",
                principalColumn: "Id");
        }

        /// <inheritdoc />
        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_Burgers_Menus_MenuId",
                table: "Burgers");

            migrationBuilder.DropForeignKey(
                name: "FK_Commandes_Burgers_BurgerId",
                table: "Commandes");

            migrationBuilder.DropForeignKey(
                name: "FK_Commandes_Clients_ClientId",
                table: "Commandes");

            migrationBuilder.DropForeignKey(
                name: "FK_Commandes_Menus_MenuId",
                table: "Commandes");

            migrationBuilder.DropIndex(
                name: "IX_Paiements_CommandeId",
                table: "Paiements");

            migrationBuilder.DropIndex(
                name: "IX_Commandes_BurgerId",
                table: "Commandes");

            migrationBuilder.DropIndex(
                name: "IX_Commandes_MenuId",
                table: "Commandes");

            migrationBuilder.DropIndex(
                name: "IX_Burgers_MenuId",
                table: "Burgers");

            migrationBuilder.DropColumn(
                name: "BurgerId",
                table: "Commandes");

            migrationBuilder.DropColumn(
                name: "MenuId",
                table: "Commandes");

            migrationBuilder.DropColumn(
                name: "ModeConsommation",
                table: "Commandes");

            migrationBuilder.DropColumn(
                name: "Paiement",
                table: "Commandes");

            migrationBuilder.DropColumn(
                name: "ImageUrl",
                table: "Burgers");

            migrationBuilder.DropColumn(
                name: "MenuId",
                table: "Burgers");

            migrationBuilder.AlterColumn<int>(
                name: "ClientId",
                table: "Commandes",
                type: "integer",
                nullable: false,
                defaultValue: 0,
                oldClrType: typeof(int),
                oldType: "integer",
                oldNullable: true);

            migrationBuilder.CreateTable(
                name: "BurgerCommande",
                columns: table => new
                {
                    BurgersId = table.Column<int>(type: "integer", nullable: false),
                    CommandesId = table.Column<int>(type: "integer", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_BurgerCommande", x => new { x.BurgersId, x.CommandesId });
                    table.ForeignKey(
                        name: "FK_BurgerCommande_Burgers_BurgersId",
                        column: x => x.BurgersId,
                        principalTable: "Burgers",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Cascade);
                    table.ForeignKey(
                        name: "FK_BurgerCommande_Commandes_CommandesId",
                        column: x => x.CommandesId,
                        principalTable: "Commandes",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.CreateTable(
                name: "BurgerMenu",
                columns: table => new
                {
                    BurgersId = table.Column<int>(type: "integer", nullable: false),
                    MenusId = table.Column<int>(type: "integer", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_BurgerMenu", x => new { x.BurgersId, x.MenusId });
                    table.ForeignKey(
                        name: "FK_BurgerMenu_Burgers_BurgersId",
                        column: x => x.BurgersId,
                        principalTable: "Burgers",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Cascade);
                    table.ForeignKey(
                        name: "FK_BurgerMenu_Menus_MenusId",
                        column: x => x.MenusId,
                        principalTable: "Menus",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.CreateTable(
                name: "CommandeMenu",
                columns: table => new
                {
                    CommandesId = table.Column<int>(type: "integer", nullable: false),
                    MenusId = table.Column<int>(type: "integer", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_CommandeMenu", x => new { x.CommandesId, x.MenusId });
                    table.ForeignKey(
                        name: "FK_CommandeMenu_Commandes_CommandesId",
                        column: x => x.CommandesId,
                        principalTable: "Commandes",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Cascade);
                    table.ForeignKey(
                        name: "FK_CommandeMenu_Menus_MenusId",
                        column: x => x.MenusId,
                        principalTable: "Menus",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.CreateIndex(
                name: "IX_Paiements_CommandeId",
                table: "Paiements",
                column: "CommandeId",
                unique: true);

            migrationBuilder.CreateIndex(
                name: "IX_BurgerCommande_CommandesId",
                table: "BurgerCommande",
                column: "CommandesId");

            migrationBuilder.CreateIndex(
                name: "IX_BurgerMenu_MenusId",
                table: "BurgerMenu",
                column: "MenusId");

            migrationBuilder.CreateIndex(
                name: "IX_CommandeMenu_MenusId",
                table: "CommandeMenu",
                column: "MenusId");

            migrationBuilder.AddForeignKey(
                name: "FK_Commandes_Clients_ClientId",
                table: "Commandes",
                column: "ClientId",
                principalTable: "Clients",
                principalColumn: "Id",
                onDelete: ReferentialAction.Cascade);
        }
    }
}
