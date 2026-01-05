<?php

namespace App\Controller\Admin;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

#[Route('/admin', name: 'admin_')]
class PaiementController extends AbstractController
{
    #[Route('/paiement', name: 'paiement')]
    public function index(): Response
    {
        return $this->render('admin/paiement/index.html.twig');
    }
}
