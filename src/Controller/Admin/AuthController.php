<?php

namespace App\Controller\Admin;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

class AuthController extends AbstractController
{
    #[Route('/admin/login', name: 'admin_login', methods: ['GET','POST'])]
    public function login(Request $request): Response
    {
        if ($request->isMethod('POST')) {
            $email = $request->request->get('email');
            $password = $request->request->get('password');

            if ($email === 'ahmadufall@gmail.com' && $password === 'ahmadu123') {
                // Redirection vers le dashboard si identifiants corrects
                return $this->redirectToRoute('admin_dashboard');
            }

            $this->addFlash('error', 'Identifiants incorrects');
        }

        return $this->render('admin/auth/login.html.twig');
    }

    #[Route('/admin/logout', name: 'admin_logout', methods: ['POST'])]
    public function logout(): void
    {
        // Géré plus tard par le firewall
    }
}
