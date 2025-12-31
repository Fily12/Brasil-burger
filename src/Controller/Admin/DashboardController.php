<?php

namespace App\Controller\Admin;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

#[Route('/admin', name: 'admin_')]
class DashboardController extends AbstractController
{
    #[Route('', name: 'home')]
    public function home(): Response
    {
        // Redirige automatiquement vers le tableau de bord
        return $this->redirectToRoute('admin_dashboard');
    }

    #[Route('/dashboard', name: 'dashboard')]
    public function dashboard(): Response
    {
        return $this->render('admin/dashboard/index.html.twig');
    }
}
