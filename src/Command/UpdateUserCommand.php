<?php

namespace App\Command;

use App\Entity\User;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Component\Console\Attribute\AsCommand;
use Symfony\Component\Console\Command\Command;
use Symfony\Component\Console\Input\InputInterface;
use Symfony\Component\Console\Output\OutputInterface;
use Symfony\Component\PasswordHasher\Hasher\UserPasswordHasherInterface;

#[AsCommand(name: 'app:update-user')]
class UpdateUserCommand extends Command
{
    public function __construct(
        private EntityManagerInterface $entityManager,
        private UserPasswordHasherInterface $passwordHasher
    ) {
        parent::__construct();
    }

    protected function execute(InputInterface $input, OutputInterface $output): int
    {
        $user = $this->entityManager->getRepository(User::class)->findOneBy(['email' => 'ahmadufall@gmail.com']);
        
        if (!$user) {
            $output->writeln('User not found!');
            return Command::FAILURE;
        }

        $hashedPassword = $this->passwordHasher->hashPassword($user, 'ahmadu123');
        $user->setPassword($hashedPassword);

        $this->entityManager->flush();

        $output->writeln('User password updated successfully!');
        return Command::SUCCESS;
    }
}