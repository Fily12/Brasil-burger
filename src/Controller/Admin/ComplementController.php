<?php

namespace App\Controller\Admin;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

#[Route('/admin', name: 'admin_')]
class ComplementController extends AbstractController
{
    #[Route('/complement', name: 'complement')]
    public function index(): Response
    {
        return $this->render('admin/complement/index.html.twig');
    }
}
